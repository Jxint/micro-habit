'use strict';

const TOKEN_PLAN_KEY = process.env.MINIMAX_API_KEY || '';
const BASE_URL = 'https://api.minimaxi.com/anthropic/messages';

exports.main = async (event, context) => {
  const { uid, date, data } = event;
  if (!uid || !date || !data) {
    return { code: 400, error: '缺少参数' };
  }

  if (!TOKEN_PLAN_KEY) {
    return { code: 500, error: 'API Key 未配置' };
  }

  const systemPrompt = [
    '你是一个温暖、轻度幽默、不说教的健康伴侣 AI。你的任务是根据用户今天的微习惯数据生成每日小结。',
    '## 风格要求',
    '- 禁止说教、禁止医学恐吓、禁止引发焦虑',
    '- 如果今天表现好，热情鼓励',
    '- 如果今天表现一般，温和引导，不要批评',
    '- 如果今天表现差，表示理解，轻松带过',
    '- 语气温暖、像朋友聊天'
  ].join('\n');

  const userMessage = [
    '## 今日微习惯数据',
    '日期：' + date,
    '完成次数：' + (data.totalCompleted || 0),
    '跳过次数：' + (data.totalSkipped || 0),
    '累计时长：' + (data.totalDurationSec || 0) + ' 秒',
    '守护时长：' + (data.guardMinutes || 0) + ' 分钟',
    '三状态：护眼 ' + (data.eyeScore || 0) + '% / 体态 ' + (data.postureScore || 0) + '% / 活力 ' + (data.vitalityScore || 0) + '%',
    '习惯渗透度：' + (data.penetration || 0),
    '',
    '## 输出要求',
    '严格按照以下 JSON 格式返回，不要有任何其他文字：',
    '{',
    '  "one_liner": "一句话点评（温暖幽默，10-25字）",',
    '  "summary": "今日总结（2-3句话，先肯定再委婉建议）",',
    '  "tomorrow_goal": "明天小目标（具体可执行，一句话）",',
    '  "encourage": "鼓励语（简短有力，5-15字）"',
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
    console.error('[llm-daily] 调用失败:', e.message);
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
    one_liner: '今日完成' + (data.totalCompleted || 0) + '次微动作',
    summary: '守护' + (data.guardMinutes || 0) + '分钟，继续加油',
    tomorrow_goal: '继续保持！',
    encourage: '你做得很好'
  };
}
