echo 'EMQX started, ready to initialize..';

EMQX_API_BASE_URL="http://emqx:18083/api/v5"

# ログイン
EMQX_RESPONSE=$(curl -s -XPOST "${EMQX_API_BASE_URL}/login" \
    -H 'Content-Type: application/json' \
    -d '{ "username": "admin", "password": "public" }')

# ログイン結果からトークンを取得
EMQX_TOKEN=$(echo ${EMQX_RESPONSE} | cut -d '"' -f 10)

# トークンを使ってユーザーを追加
curl -s -XPOST "${EMQX_API_BASE_URL}/authentication/password_based:built_in_database/users" \
    -H 'Content-Type: application/json' \
    -H "Authorization: Bearer ${EMQX_TOKEN}" \
    -d '{ "user_id": "subuser", "password": "password" }'

curl -s -XPOST "${EMQX_API_BASE_URL}/authentication/password_based:built_in_database/users" \
    -H 'Content-Type: application/json' \
    -H "Authorization: Bearer ${EMQX_TOKEN}" \
    -d '{ "user_id": "pubuser", "password": "password" }'

echo 'done!'
