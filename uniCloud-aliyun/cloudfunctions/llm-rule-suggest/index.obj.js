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
    '你是一个健康行为分析 AI。根据用户最近的微习惯完成数据，判断是否需要建议新的触发规则。',
    '## 规则',
    '- 如果用户某类动作完成率低于30%，建议增加该类动作的触发',
    '- 如果用户某个时段完成率特别高，不建议修改该时段',
    '- 如果有明显的模式（如某时段总是跳过），建议调整触发时机或动作类型',
    '- 建议要具体、可执行'
  ].join('\n');

  const userMessage = [
    '## 近期微习惯数据',
    '各类别完成率：' + JSON.stringify(data.completionRateByCategory || {}),
    '各时段完成率：' + JSON.stringify(data.completionRateByHour || {}),
    '各类别平均连续使用分钟：' + JSON.stringify(data.avgContinuousMinutesByCategory || {}),
    '近期触发记录：' + JSON.stringify(data.recentTriggers || []),
    '',
    '## 输出要求',
    '严格按照以下 JSON 格式返回，不要有任何其他文字：',
    '{',
    '  "shouldSuggest": true或false,',
    '  "rule": {',
    '    "description": "自然语言描述",',
    '    "suggestedActions": ["action_id1", "action_id2"]',
    '  }',
    '}',
    '如果不需要建议新规则，shouldSuggest 设为 false，rule 设为 null'
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

    return { code: 0, data: { shouldSuggest: false, rule: null }, fromLlm: false };
  } catch (e) {
    console.error('[llm-rule-suggest] 调用失败:', e.message);
    return { code: 0, data: { shouldSuggest: false, rule: null }, fromLlm: false };
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
