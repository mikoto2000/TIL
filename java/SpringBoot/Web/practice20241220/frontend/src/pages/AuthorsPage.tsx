import { useEffect, useState } from "react";
import { BASE_URL } from "../config";
import { AuthorSearchControllerApiFactory, Configuration } from "../api";
import { Table } from "../components/Table/Table";
import { useLocation } from "react-router";
import queryString from "query-string";

type AuthorsPageProps = {
};

export const AuthorsPage: React.FC<AuthorsPageProps> = ({ }) => {
  const [authors, setAuthors] = useState<any[] | undefined>([])

  const search = useLocation().search;
  const queryParams = queryString.parse(search);
  const id: any = queryParams['id'];
  const name: any = queryParams['name'];
  const sort: any = queryParams['sort'];
  const page: any = queryParams['page'];
  const size: any = queryParams['size'];

  useEffect(() => {
    (async () => {
      const authorApiFactory = AuthorSearchControllerApiFactory(new Configuration(), BASE_URL);
      const authorsResult = await authorApiFactory.executeSearchAuthorGet({
        id: id ? id : undefined,
        name: name ? name : undefined,
        page: page ? page : undefined,
        size: size ? size : undefined,
        sort: sort ? sort : undefined,
      })
      console.log(authorsResult);
      const authorsData = authorsResult.data;
      console.log(authorsData);
      setAuthors(authorsData._embedded?.authors);
    })();
  }, [search]);

  return (
    <>
      {/* TODO: ページ遷移せずに、 search の更新だけを行うように修正 */}
      <form action="/authors">
      <div>
        <div>
          <label>Id:</label>
          <input type="number" name="id" defaultValue={id}></input>
        </div>
        <div>
          <label>Name:</label>
          <input type="text" name="name" defaultValue={name}></input>
        </div>
        <div>
        <button type="submit">検索</button>
        </div>
      </div>
      </form>
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
    </>
  )
}
