import { PrismaClient } from '@prisma/client'

const prisma = new PrismaClient()

async function main() {
  // Author を生成
  const createdAuthor = await prisma.author.create({
    data: {
      name: 'mikoto2000',
    },
  })
  console.dir(`createdAuthor: ${JSON.stringify(createdAuthor)}`);

  // Book と、 Author へのリレーションシップを生成
  const createdBook = await prisma.book.create({
    data: {
      title: 'mikoto2000\'s history',
      authors: {
        create: [
          {
            authorId: createdAuthor.id
          }
        ]
      }
    },
  })
  console.dir(`createdBook: ${JSON.stringify(createdBook)}`);

  // 作成した Book の検索(中間テーブル・Author を JOIN して、戻り値に含める)
  const foundBook = await prisma.book.findMany({
    include: {
      authors: {
        include: {
          author: {}
        }
      }
    }
  });
  console.dir(`foundBook: ${JSON.stringify(foundBook)}`);
  console.dir(`プロパティ取得の練習: { title: ${foundBook[0].title}, Author: ${foundBook[0].authors.map((e) => e.author.name)}}`);

  // Book を削除(中間テーブルの要素と Book 自体の削除。中間テーブルはカスケード設定をしているので自動で削除される)
  const deletedBook = await prisma.book.delete({
    where: {
      id: createdBook.id
    },
  });
  console.dir(`deletedBook: ${JSON.stringify(deletedBook)}`);

  // Author を削除
  const deletedAuthor = await prisma.author.delete({
    where: {
      id: createdAuthor.id
    }
  });
  console.dir(`deletedAuthor: ${JSON.stringify(deletedAuthor)}`);
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
