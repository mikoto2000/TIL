import { useEffect, useState } from "react";
import { BASE_URL } from "../config";
import { BookMasterSearchControllerApiFactory, Configuration } from "../api";
import { Table } from "../components/Table/Table";
import { useLocation } from "react-router";
import queryString from "query-string";

type BookMastersPageProps = {
};

export const BookMastersPage: React.FC<BookMastersPageProps> = ({ }) => {

  const [bookMasters, setBookMasters] = useState<any[] | undefined>([])

  const search = useLocation().search;
  const queryParams = queryString.parse(search);
  const id: any = queryParams['id'];
  const name: any = queryParams['name'];
  const publicationDateBegin: any = queryParams['publicationDateBegin'];
  const publicationDateEnd: any = queryParams['publicationDateEnd'];
  const sort: any = queryParams['sort'];
  const page: any = queryParams['page'];
  const size: any = queryParams['size'];

  console.log(publicationDateBegin);

  useEffect(() => {
    (async () => {

      const bookMasterApiFactory = BookMasterSearchControllerApiFactory(new Configuration(), BASE_URL);
      const bookMastersResult = await bookMasterApiFactory.executeSearchBookmasterGet({
        id: id ? id : undefined,
        name: name ? name : undefined,
        publicationDateBegin: publicationDateBegin ? publicationDateBegin : undefined,
        publicationDateEnd: publicationDateEnd ? publicationDateEnd : undefined,
        page: page ? page : undefined,
        size: size ? size : undefined,
        sort: sort ? sort : undefined,
      });
      console.log(bookMastersResult);
      const bookMastersData = bookMastersResult.data;
      console.log(bookMastersData);

      setBookMasters(bookMastersData._embedded?.bookMasters);

    })();
  }, [search]);


  return (
    <>
      {/* TODO: ページ遷移せずに、 search の更新だけを行うように修正 */}
      <form action="/bookMasters">
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
          {
            name: "Publication Date",
            onClick: () => {
            }
          },
          {
            name: "Authors",
            onClick: () => {
            }
          },
        ]}
        contentInfo={[
          { getValueFunc: (row: any) => row.id },
          { getValueFunc: (row: any) => row.name },
          { getValueFunc: (row: any) => row.publicationDate },
          { getValueFunc: (row: any) => row.author.map((e: any) => e.name).join(", ") },
        ]}
        content={bookMasters}
      />
    </>
  )
}
