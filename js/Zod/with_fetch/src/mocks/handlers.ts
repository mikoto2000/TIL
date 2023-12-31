import { http, HttpResponse } from "msw";

import { User } from "../types/User";
 
export const handlers = [
  // パースを成功させるためのハンドラ
  http.get("http://example.com/users", () => {
    const user = {
      firstName: "Mikoto",
      lastName: "Ohyuki",
      age: 500000000000
    } as User;
    return HttpResponse.json([user]);
  }),
  // パースを失敗させるためのハンドラ
  http.get("http://example.com/invalid-users", () => {
    const user = {
      lastName: "Ohyuki",
    };
    return HttpResponse.json([user]);
  }),
]

