import { z } from "zod";

export type User = z.infer<typeof UserSchema>;

// ランタイム型の定義
export const UserSchema = z.object({
  firstName: z.string(),
  lastName: z.string(),
  age: z.number(),
});

