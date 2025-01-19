"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
const client_1 = require("@prisma/client");
const prisma = new client_1.PrismaClient();
function main() {
    return __awaiter(this, void 0, void 0, function* () {
        // User を生成
        const createdUser = yield prisma.user.create({
            data: {
                name: 'mikoto2000',
                email: 'mikoto2000@gmail.com',
            },
        });
        console.dir(`createdUser: ${JSON.stringify(createdUser)}`);
        // user を検索
        const foundUser = yield prisma.user.findUnique({
            where: {
                email: 'mikoto2000@gmail.com',
            }
        });
        console.dir(`foundUser: ${JSON.stringify(foundUser)}`);
        // user を更新
        if (foundUser) {
            const updatedUser = yield prisma.user.update({
                where: {
                    email: foundUser.email,
                },
                data: {
                    name: 'mikoto2000+prisma@gmail.com',
                },
            });
            console.dir(`updatedUser: ${JSON.stringify(updatedUser)}`);
        }
        // user を検索
        const foundUser2 = yield prisma.user.findMany({
            where: {
                name: 'mikoto2000+prisma@gmail.com',
            }
        });
        console.dir(`foundUser2: ${JSON.stringify(foundUser2)}`);
        // user を削除
        const deletedUser = yield prisma.user.delete({
            where: {
                id: createdUser.id
            }
        });
        console.dir(`deletedUser: ${JSON.stringify(deletedUser)}`);
    });
}
main()
    .then(() => __awaiter(void 0, void 0, void 0, function* () {
    yield prisma.$disconnect();
}))
    .catch((e) => __awaiter(void 0, void 0, void 0, function* () {
    console.error(e);
    yield prisma.$disconnect();
    process.exit(1);
}));
