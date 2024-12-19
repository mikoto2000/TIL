import { useEffect, useState } from "react";
import { BASE_URL } from "../config";
import { AuthorEntityControllerApiFactory, Configuration } from "../api";
import { Table } from "../components/Table/Table";

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
    <Table
      headerInfo={[
        {
          name: "Id",
          onClick: () => {
          }
        },
        {
          name: "Name",
          onClick: () => {
          }
        },
      ]}
      contentInfo={[
        { getValueFunc: (row: any) => row.id },
        { getValueFunc: (row: any) => row.name },
      ]}
      content={authors}
    />
  )
}
