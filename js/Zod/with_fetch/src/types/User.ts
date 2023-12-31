import { z } from "zod";

// 静的型定義
export type User = z.infer<typeof UserSchema>;
export type Users = z.infer<typeof UsersSchema>;

// ランタイム型の定義
export const UserSchema = z.object({
  firstName: z.string(),
  lastName: z.string(),
  age: z.number(),
});
export const UsersSchema = z.array(UserSchema);

