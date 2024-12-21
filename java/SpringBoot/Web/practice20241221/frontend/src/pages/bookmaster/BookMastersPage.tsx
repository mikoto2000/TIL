import { useEffect, useState } from "react";
import { BASE_URL } from "../../config";
import { EchoControllerApiFactory, Configuration } from "../../api";
import { Table } from "../../components/Table/Table";
import { Link, useLocation, useNavigate } from "react-router";
import queryString from "query-string";
import { updateOrder } from "../../util/util";

type BookMastersPageProps = {
};

export const BookMastersPage: React.FC<BookMastersPageProps> = ({ }) => {

  const [bookMasters, setBookMasters] = useState<any[] | undefined>([])

  const navigate = useNavigate();
  const location = useLocation();
  const currentPath = location.pathname
  const search = location.search;
  const queryParams = queryString.parse(search);
  const id: any = queryParams['id'];
  const name: any = queryParams['name'];
  const publicationDateBegin: any = queryParams['publicationDateBegin'];
  const publicationDateEnd: any = queryParams['publicationDateEnd'];
  const ndcCategoryName: any = queryParams['ndcCategoryName'];
  const sort: any = queryParams['sort'];
  const page: any = queryParams['page'];
  const size: any = queryParams['size'];

  console.log(publicationDateBegin);

  useEffect(() => {
    (async () => {

      const bookMasterApiFactory = EchoControllerApiFactory(new Configuration(), BASE_URL);
      const bookMastersResult = await bookMasterApiFactory.searchBookMasters({
        id: id ? id : undefined,
        name: name ? name : undefined,
        publicationDateBegin: publicationDateBegin ? publicationDateBegin : undefined,
        publicationDateEnd: publicationDateEnd ? publicationDateEnd : undefined,
        ndcCategoryName: ndcCategoryName ? ndcCategoryName : undefined,
        page: page ? page : undefined,
        size: size ? size : undefined,
        sort: sort ? sort : undefined,
      });
      console.log(bookMastersResult);
      const bookMastersData = bookMastersResult.data;
      console.log(bookMastersData);

      setBookMasters(bookMastersData?.content);

    })();
  }, [search]);


  return (
    <>
      <Link to="/bookMasters/create">新規登録</Link>
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
          <label>Publication Date:</label>
          <input type="date" name="publicationDateBegin" defaultValue={publicationDateBegin}></input>
          ～
          <input type="date" name="publicationDateEnd" defaultValue={publicationDateEnd}></input>
        </div>
        <div>
          <label>Ndc Category:</label>
          <input type="text" name="ndcCategoryName" defaultValue={ndcCategoryName}></input>
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
              const newUrl = updateOrder(currentPath, queryParams, sort, "id")
              navigate(newUrl)
            }
          },
          {
            name: "Name",
            onClick: () => {
              const newUrl = updateOrder(currentPath, queryParams, sort, "name")
              navigate(newUrl)
            }
          },
          {
            name: "Publication Date",
            onClick: () => {
              const newUrl = updateOrder(currentPath, queryParams, sort, "publication_date")
              navigate(newUrl)
            }
          },
          {
            name: "Authors",
            onClick: () => {
              const newUrl = updateOrder(currentPath, queryParams, sort, "author")
              navigate(newUrl)
            }
          },
          {
            name: "Ndc Category",
            onClick: () => {
              const newUrl = updateOrder(currentPath, queryParams, sort, "n.name")
              navigate(newUrl)
            }
          },
        ]}
        contentInfo={[
          { getValueFunc: (row: any) => row.id },
          { getValueFunc: (row: any) => row.name },
          { getValueFunc: (row: any) => row.publicationDate },
          { getValueFunc: (row: any) => row.author },
          { getValueFunc: (row: any) => row.ndcCategoryName },
        ]}
        content={bookMasters}
      />
    </>
  )
}
