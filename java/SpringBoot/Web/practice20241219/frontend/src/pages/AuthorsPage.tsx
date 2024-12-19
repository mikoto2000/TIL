import { useEffect, useState } from "react";
import { BASE_URL } from "../config";
import { AuthorEntityControllerApiFactory, Configuration, EntityModelAuthor } from "../api";

type AuthorsPageProps = {
};

export const AuthorsPage: React.FC<AuthorsPageProps> = ({ }) => {
  const [authors, setAuthors] = useState<EntityModelAuthor[] | undefined>([])
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
    <ul>
      {
        authors
          ?
          authors.map((e) => <li>Name: {e.name}</li>)
          :
          "表示するものがありませんでした。"
      }
    </ul>
  )
}
