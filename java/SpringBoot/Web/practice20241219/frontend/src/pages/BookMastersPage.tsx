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
    <table>
      <thead>
        <th>Id</th>
        <th>Name</th>
        <th>Publication Date</th>
        <th>Authors</th>
      </thead>
      <tbody>
        {
          bookMasters
            ?
            bookMasters.map((e) => <tr>
              <td>{e.id}</td>
              <td>{e.name}</td>
              <td>{e.publicationDate}</td>
              <td>{JSON.stringify(e.author)}</td>
            </tr>)
            :
            "表示するものがありませんでした。"
        }
      </tbody>
    </table>
  )
}
