'use strict';

const TOKEN_PLAN_KEY = process.env.MINIMAX_API_KEY || '';
const BASE_URL = 'https://api.minimaxi.com/anthropic/messages';

exports.main = async (event, context) => {
  const { uid, data } = event;
  if (!uid || !data) {
    return { code: 400, error: '缺少参数' };
  }

  if (!TOKEN_PLAN_KEY) {
    return { code: 500, error: 'API Key 未配置' };
  }

  const systemPrompt = [
    '你是一个温暖、轻度幽默、不说教的健康伴侣 AI。根据用户本周的微习惯数据生成每周健康报告。',
    '## 风格要求',
    '- 禁止说教、禁止医学恐吓、禁止引发焦虑',
    '- 亮点要真诚鼓励',
    '- 不足要温和建议，不要批评',
    '- 下周目标要具体、可量化、可实现',
    '- 语气像朋友在给你做周总结'
  ].join('\n');

  const userMessage = [
    '## 本周微习惯数据',
    '周完成总次数：' + (data.totalCompleted || 0),
    '累计时长：' + (data.totalDurationSec || 0) + ' 秒',
    '日均守护：' + (data.avgGuardMinutes || 0) + ' 分钟',
    '平均渗透度：' + (data.avgPenetration || 0),
    '表现最佳日：' + (data.bestDay || '无') + '（' + (data.bestDayCount || 0) + '次）',
    '表现最少日：' + (data.worstDay || '无') + '（' + (data.worstDayCount || 0) + '次）',
    '',
    '## 输出要求',
    '严格按照以下 JSON 格式返回，不要有任何其他文字：',
    '{',
    '  "title": "周报标题（温暖有趣，8-15字）",',
    '  "highlight": "亮点总结（1-2句，真诚肯定）",',
    '  "improvement": "待改善项（1-2句，温和建议）",',
    '  "suggestions": ["建议1", "建议2", "建议3"],',
    '  "nextWeekGoal": "下周目标（具体可量化，一句话）",',
    '  "tone": "整体基调（great/good/okay/needs_care）"',
    '}'
  ].join('\n');

  try {
    const response = await uniCloud.httpclient.request(BASE_URL, {
      method: 'POST',
      headers: {
        'x-api-key': TOKEN_PLAN_KEY,
        'anthropic-version': '2023-06-01',
        'Content-Type': 'application/json'
      },
      data: {
        model: 'MiniMax-M2.7',
        max_tokens: 512,
        system: systemPrompt,
        messages: [
          { role: 'user', content: userMessage }
        ]
      },
      dataType: 'json',
      timeout: 15000
    });

    const content = response.data?.content?.[0]?.text || '';
    const parsed = safeParseJson(content);

    if (parsed) {
      return { code: 0, data: parsed, fromLlm: true };
    }

    return { code: 0, data: buildFallback(data), fromLlm: false };
  } catch (e) {
    console.error('[llm-weekly] 调用失败:', e.message);
    return { code: 0, data: buildFallback(data), fromLlm: false };
  }
};

function safeParseJson(rawText) {
  try {
    return JSON.parse(rawText);
  } catch (_) {}
  const match = rawText.match(/\{[\s\S]*\}/);
  if (match) {
    try {
      return JSON.parse(match[0]);
    } catch (_) {}
  }
  return null;
}

function buildFallback(data) {
  return {
    title: '本周健康小结',
    highlight: '本周完成' + (data.totalCompleted || 0) + '次微动作，累计守护' + (data.avgGuardMinutes || 0) * 7 + '分钟',
    improvement: '下周可以尝试更多样化的动作组合',
    suggestions: ['尝试每天固定时间做一个微动作', '关注完成率最低的动作类型', '给自己设定一个小奖励'],
    nextWeekGoal: '每天至少完成3次微动作',
    tone: 'okay'
  };
}
