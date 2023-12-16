import { z } from "zod";

// ランタイム型の定義
const UserSchema = z.object({
  firstName: z.string(),
  lastName: z.string(),
  age: z.number(),
});

// 静的型定義
type User = z.infer<typeof UserSchema>;

// User として妥当な値
const validUser = {
  firstName: "firstName",
  lastName: "lastName",
  age: 123,
};

// User として不当な値
const invalidUser = {
  name: "firstName lastName",
  age: 123,
};

// パース
console.log("== エラー時に例外を送出するパース ==");

try {
  const user1: User = UserSchema.parse(validUser);
  console.log(JSON.stringify(user1));
} catch (error) {
  console.log(error);
}

try {
  const user2: User = UserSchema.parse(invalidUser);
  console.log(JSON.stringify(user2));
} catch (error) {
  console.log(JSON.stringify(error));
}

console.log("\n== パース結果を戻り値として返却するパース ==");

const user3Result = UserSchema.safeParse(validUser);
console.log(JSON.stringify(user3Result));

const user4Result = UserSchema.safeParse(invalidUser);
console.log(JSON.stringify(user4Result));

