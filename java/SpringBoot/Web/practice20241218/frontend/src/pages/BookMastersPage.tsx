import { useEffect, useState } from "react";
import { BASE_URL } from "../config";
import { BookMasterEntityControllerApiFactory, Configuration, EntityModelBookMaster } from "../api";

type BookMastersPageProps = {
};

export const BookMastersPage: React.FC<BookMastersPageProps> = ({ }) => {

  const [bookMasters, setBookMasters] = useState<EntityModelBookMaster[] | undefined>([])

  useEffect(() => {
    (async () => {
      const bookMasterApiFactory = BookMasterEntityControllerApiFactory(new Configuration(), BASE_URL);
      const bookMastersResult = await bookMasterApiFactory.getCollectionResourceBookmasterGet({});
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
          bookMasters.map((e) => <li>Name: {e.name}</li>)
          :
          "表示するものがありませんでした。"
      }
    </ul>
  )
}
