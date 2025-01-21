-- CreateTable
CREATE TABLE "Book" (
    "id" SERIAL NOT NULL,
    "title" TEXT NOT NULL,

    CONSTRAINT "Book_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "Author" (
    "id" SERIAL NOT NULL,
    "name" TEXT NOT NULL,

    CONSTRAINT "Author_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "BooksOnAuthors" (
    "id" SERIAL NOT NULL,
    "bookId" INTEGER NOT NULL,
    "authorId" INTEGER NOT NULL,

    CONSTRAINT "BooksOnAuthors_pkey" PRIMARY KEY ("id")
);

-- AddForeignKey
ALTER TABLE "BooksOnAuthors" ADD CONSTRAINT "BooksOnAuthors_bookId_fkey" FOREIGN KEY ("bookId") REFERENCES "Book"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "BooksOnAuthors" ADD CONSTRAINT "BooksOnAuthors_authorId_fkey" FOREIGN KEY ("authorId") REFERENCES "Author"("id") ON DELETE RESTRICT ON UPDATE CASCADE;
