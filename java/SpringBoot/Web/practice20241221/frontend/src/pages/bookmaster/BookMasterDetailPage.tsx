import { useEffect, useState } from "react";
import { BookMasterEntityControllerApiFactory, Configuration, EntityModelBookMaster } from "../../api";
import { BASE_URL } from "../../config";
import { Link, useParams } from "react-router";

type BookMasterDetailPageProps = {
};

export const BookMasterDetailPage: React.FC<BookMasterDetailPageProps> = ({ }) => {

  const { id } = useParams();

  const [bookMaster, setBookMaster] = useState<EntityModelBookMaster | undefined>(undefined);
  useEffect(() => {
    (async () => {
      if (id) {
        const api = BookMasterEntityControllerApiFactory(new Configuration(), BASE_URL);

        const bookMasterResult = await api.getItemResourceBookmasterGet({
          id
        });

        setBookMaster(bookMasterResult.data);
      }
    })();
  }, []);

  return (
    bookMaster
      ?
      <>
        <table>
          <tbody>
            <tr>
              <td>Id:</td>
              <td>{(bookMaster as any).id}</td>
            </tr>
            <tr>
              <td>Name:</td>
              <td>{bookMaster.name}</td>
            </tr>
            <tr>
              <td>Publication Date:</td>
              <td>{bookMaster.publicationDate}</td>
            </tr>
            <tr>
              <td>authors:</td>
              <td>
                {
                  (bookMaster as any)._embedded.author
                    ?
                    (bookMaster as any)._embedded.author.map((e: any) => e.name).join(", ")
                    :
                    <></>
                }
              </td>
            </tr>
            <tr>
              <td>Ndc Category:</td>
              <td>{(bookMaster as any)._embedded.ndcCategory.name}</td>
            </tr>
          </tbody>
        </table>
        <Link to="/bookMasters">一覧に戻る</Link>
      </>
      :
      <></>
  )
}
