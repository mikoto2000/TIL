export type User = {
  /**
   * 必須。`USER-` で始まり、以降数値の文字列。
   */
  userCode: `USER-${number}`;

  /**
   * 必須。 1 文字以上の文字列。
   */
  userName: string;

  /**
   * 必須。ひとつ以上のメールアドレス。
   */
  mail: string[];

  /**
   * オプション。誕生日。
   * ※ 日時操作したい場合にはその都度変換する
   */
  birthday?: string;

  /**
   * オプション。謎日時。
   * ※ 日時操作したい場合にはその都度変換する
   */
  datetime?: string;

  /**
   * オプション。ゼロ個以上の URL。
   */
  homepage?: URL[];
};

