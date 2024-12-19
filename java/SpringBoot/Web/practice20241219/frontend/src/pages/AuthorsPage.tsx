import { useEffect, useState } from "react";
import { BASE_URL } from "../config";
import { AuthorEntityControllerApiFactory, Configuration } from "../api";

type AuthorsPageProps = {
};

export const AuthorsPage: React.FC<AuthorsPageProps> = ({ }) => {
  const [authors, setAuthors] = useState<any[] | undefined>([])
  useEffect(() => {
    (async () => {
      const authorApiFactory = AuthorEntityControllerApiFactory(new Configuration(), BASE_URL);
      const authorsResult = await authorApiFactory.getCollectionResourceAuthorGet({})
      console.log(authorsResult);
      const authorsData = authorsResult.data;
      console.log(authorsData);
      setAuthors(authorsData._embedded?.authors);
    })();
  }, []);

  return (
    <table>
      <thead>
        <th>Id</th>
        <th>Name</th>
      </thead>
      <tbody>
        {
          authors
            ?
            authors.map((e) => <tr>
              <td>{e.id}</td>
              <td>{e.name}</td>
            </tr>)
            :
            "表示するものがありませんでした。"
        }
      </tbody>
    </table>
  )
}
