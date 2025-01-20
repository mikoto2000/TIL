import { PrismaClient } from '@prisma/client'

const prisma = new PrismaClient()

async function main() {
  // User を生成
  const createdUser = await prisma.user.create({
    data: {
      name: 'mikoto2000',
      email: 'mikoto2000@gmail.com',
    },
  })
  console.dir(`createdUser: ${JSON.stringify(createdUser)}`);

  // user を検索
  const foundUser = await prisma.user.findUnique({
    where: {
      email: 'mikoto2000@gmail.com',
    }
  });
  console.dir(`foundUser: ${JSON.stringify(foundUser)}`);

  // user を更新
  if (foundUser) {
    const updatedUser = await prisma.user.update({
      where: {
        email: foundUser.email,
      },
      data: {
        name: 'mikoto2000+prisma@gmail.com',
      },
    })
    console.dir(`updatedUser: ${JSON.stringify(updatedUser)}`);
  }

  // user を検索
  const foundUser2 = await prisma.user.findMany({
    where: {
      name: 'mikoto2000+prisma@gmail.com',
    }
  });
  console.dir(`foundUser2: ${JSON.stringify(foundUser2)}`);

  // user を削除
  const deletedUser = await prisma.user.delete({
    where: {
      id: createdUser.id
    }
  });
  console.dir(`deletedUser: ${JSON.stringify(deletedUser)}`);
}

main()
  .then(async () => {
    await prisma.$disconnect()
  })
  .catch(async (e) => {
    console.error(e)
    await prisma.$disconnect()
    process.exit(1)
  })
