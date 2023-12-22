import { z } from "zod";

enum ShapeType {
  BOX = "box",
  CIRCLE = "circle",
};

// ランタイム型の定義

// 矩形
const BoxSchema = z.object({
  type: z.literal(ShapeType.BOX),
  height: z.number(),
  width: z.number(),
});

// 円
const CircleSchema = z.object({
  type: z.literal(ShapeType.CIRCLE),
  radius: z.number(),
});

// シェイプ
const ShapeSchema = z.union([BoxSchema, CircleSchema]);

// 静的型定義
type Box = z.infer<typeof BoxSchema>;
type Circle = z.infer<typeof CircleSchema>;
type Shape = z.infer<typeof ShapeSchema>;

// Box として妥当な値
const validBox = {
  type: "box",
  width: 10,
  height: 11,
};

// パース
console.log("== 妥当な Box を Shape としてパース ==");
const parseBoxByShapeResult = ShapeSchema.safeParse(validBox);
if (parseBoxByShapeResult.success) {
  console.log(`パース成功: ${JSON.stringify(parseBoxByShapeResult.data)}`);
} else {
  console.log(`パース失敗: ${parseBoxByShapeResult.error}`);
}
console.log(JSON.stringify(parseBoxByShapeResult));
console.log();

console.log("== 妥当な Box を Box としてパース ==");
const parseBoxByBoxResult = ShapeSchema.safeParse(validBox);
if (parseBoxByShapeResult.success) {
  console.log(`パース成功: ${JSON.stringify(parseBoxByShapeResult.data)}`);
} else {
  console.log(`パース失敗: ${parseBoxByShapeResult.error}`);
}
console.log(JSON.stringify(parseBoxByBoxResult));
console.log();

// Circle として妥当な値
const validCircle = {
  type: "circle",
  radius: 12,
};

console.log("== 妥当な Circle を Shape としてパース ==");
const parseCircleByShapeResult = ShapeSchema.safeParse(validCircle);
if (parseCircleByShapeResult.success) {
  console.log(`パース成功: ${JSON.stringify(parseCircleByShapeResult.data)}`);
} else {
  console.log(`パース失敗: ${parseCircleByShapeResult.error}`);
}
console.log(JSON.stringify(parseCircleByShapeResult));
console.log();


console.log("== 妥当な Circle を Circle としてパース ==");
const parseCircleByCircleResult = ShapeSchema.safeParse(validCircle);
if (parseCircleByCircleResult.success) {
  console.log(`パース成功: ${JSON.stringify(parseCircleByCircleResult.data)}`);
} else {
  console.log(`パース失敗: ${parseCircleByCircleResult.error}`);
}
console.log(JSON.stringify(parseCircleByCircleResult));
console.log();


// Circle としても Box としても不当な値
const invalidShape = {
  unknown: 13,
};

console.log("== 不当な Shape を Shape としてパース ==");
const parseInvalidByShapeResult = ShapeSchema.safeParse(invalidShape);
if (parseInvalidByShapeResult.success) {
  console.log(`パース成功: ${JSON.stringify(parseInvalidByShapeResult.data)}`);
} else {
  console.log(`パース失敗: ${parseInvalidByShapeResult.error}`);
}
console.log(JSON.stringify(parseInvalidByShapeResult));
console.log();


console.log("== 不当な Shape を Box としてパース ==");
const parseInvalidByBoxResult = BoxSchema.safeParse(invalidShape);
if (parseInvalidByBoxResult.success) {
  console.log(`パース成功: ${JSON.stringify(parseInvalidByBoxResult.data)}`);
} else {
  console.log(`パース失敗: ${parseInvalidByBoxResult.error}`);
}
console.log(JSON.stringify(parseInvalidByBoxResult));


console.log("== 不当な Shape を Circle としてパース ==");
const parseInvalidByCircleResult = CircleSchema.safeParse(invalidShape);
if (parseInvalidByCircleResult.success) {
  console.log(`パース成功: ${JSON.stringify(parseInvalidByCircleResult.data)}`);
} else {
  console.log(`パース失敗: ${parseInvalidByCircleResult.error}`);
}
console.log(JSON.stringify(parseInvalidByCircleResult));
