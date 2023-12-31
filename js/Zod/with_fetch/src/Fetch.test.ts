import { expect, test } from "vitest";
import { User, Users, UsersSchema } from "./types/User";

test("valid user", async () => {
  const httpResponse = await fetch("http://example.com/users");

  if (httpResponse.ok)  {
    const responseBody = await httpResponse.json();
    const parseResult = UsersSchema.safeParse(responseBody);

    if (parseResult.success) {
      expect(parseResult.data.length).toBe(1);
      expect(parseResult.data).toEqual([
        {
          firstName: "Mikoto",
          lastName: "Ohyuki",
          age: 500000000000
        }]);
    } else {
      throw Error(`パース失敗: ${parseResult.error}`);
    }
  } else {
    // とりあえず Zod の使い方の勉強なので異常系は省略...
    throw Error(`レスポンスが 200 台以外: ${JSON.stringify(httpResponse)}`);
  }
})

test("invalid user", async () => {
  const httpResponse = await fetch("http://example.com/invalid-users");

  if (httpResponse.ok)  {
    const responseBody = await httpResponse.json();
    const parseResult = UsersSchema.safeParse(responseBody);

    if (parseResult.success) {
      throw Error("パースに成功してはいけない");
    } else {
      parseResult.error.errors.forEach((e) => {

        // path はフィールドによって変わるので、存在確認のみ
        // 本来この辺りは Schema の単体テストでカバーするもの
        expect(e).toHaveProperty("code");
        expect(e.code).toEqual("invalid_type");
        expect(e).toHaveProperty("message");
        expect(e.message).toEqual("Required");
        expect(e).toHaveProperty("path");
      });
    }
  } else {
    // とりあえず Zod の使い方の勉強なので異常系は省略...
    throw Error(`レスポンスが 200 台以外: ${JSON.stringify(httpResponse)}`);
  }

})

