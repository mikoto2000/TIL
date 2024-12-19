import { useEffect, useState } from "react";
import { BASE_URL } from "../config";
import { BookMasterSearchControllerApiFactory, Configuration } from "../api";

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
    <ul>
      {
        bookMasters
          ?
          bookMasters.map((e) => <li>Id: {e.id}, Name: {e.name}, publicationDate: {e.publicationDate}, Authors: {JSON.stringify(e.author)}</li>)
          :
          "表示するものがありませんでした。"
      }
    </ul>
  )
}
