import { useEffect, useState } from "react";
import { BASE_URL } from "../config";
import { BookMasterSearchControllerApiFactory, Configuration } from "../api";
import { Table } from "../components/Table/Table";

type BookMastersPageProps = {
};

export const BookMastersPage: React.FC<BookMastersPageProps> = ({ }) => {

  const [bookMasters, setBookMasters] = useState<any[] | undefined>([])

  useEffect(() => {
    (async () => {
      const bookMasterApiFactory = BookMasterSearchControllerApiFactory(new Configuration(), BASE_URL);
      const bookMastersResult = await bookMasterApiFactory.executeSearchBookmasterGet({});
      console.log(bookMastersResult);
      const bookMastersData = bookMastersResult.data;
      console.log(bookMastersData);

      setBookMasters(bookMastersData._embedded?.bookMasters);
    })();
  }, []);


  return (
    <Table
      headerInfo={[
        { name: "Id" },
        { name: "Name" },
        { name: "Publication Date" },
        { name: "Authors" },
      ]}
      contentInfo={[
        { getValueFunc: (row: any) => row.id },
        { getValueFunc: (row: any) => row.name },
        { getValueFunc: (row: any) => row.publicationDate },
        { getValueFunc: (row: any) => row.author.map((e: any) => e.name).join(", ") },
      ]}
      content={bookMasters}
    />
  )
}
