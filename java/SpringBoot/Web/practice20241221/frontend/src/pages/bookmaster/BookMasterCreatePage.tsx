//import { BASE_URL } from "../../config";
//import { BookMasterEntityControllerApiFactory, Configuration } from "../../api";

import { useEffect, useState } from "react";
import { BookMasterEntityControllerApiFactory, Configuration, EntityModelNdcCategory, NdcCategoryEntityControllerApiFactory } from "../../api";
import { BASE_URL } from "../../config";

type BookMasterCreatePageProps = {
};

export const BookMasterCreatePage: React.FC<BookMasterCreatePageProps> = ({ }) => {

  //const bookMasterApiFactory = BookMasterEntityControllerApiFactory(new Configuration(), BASE_URL);
  const [ndcCategories, setNdcCategories] = useState<EntityModelNdcCategory[] | undefined>([]);

  useEffect(() => {
    (async () => {
      const ndcCategoryApi = NdcCategoryEntityControllerApiFactory(new Configuration(), BASE_URL);
      const ndcCategoryResult = await ndcCategoryApi.getCollectionResourceNdccategoryGet({});
      setNdcCategories(ndcCategoryResult.data._embedded?.ndcCategories);
    })();
  }, []);

  const handleSubmitClick = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.preventDefault();
    console.log(event);
    const form: any = event.currentTarget.form;
    console.log(form);
    const name = form.name.value;
    const publicationDate = form.publicationDate.value;
    //const author = form.author;
    const ndcCategory = form.ndcCategory.value;

    const api = BookMasterEntityControllerApiFactory(new Configuration(), BASE_URL);

    console.log(JSON.stringify({
      bookMasterRequestBody: {
        name,
        publicationDate,
        ndcCategory
      }
    }));


    api.postCollectionResourceBookmasterPost({
      bookMasterRequestBody: {
        name,
        publicationDate,
        ndcCategory
      }
    })
  }

  return (
    <>
      <form name="register">
        <div>
          <div>
            <label>Name:</label>
            <input type="text" name="name"></input>
          </div>
          <div>
            <label>Publication Date:</label>
            <input type="date" name="publicationDate"></input>
          </div>
          <div>
            <label>Author:</label>
            <input type="text" name="author"></input>
          </div>
          <div>
            <label>NdcCategory:</label>
            <select name="ndcCategory">
              {
                ndcCategories
                  ?
                  ndcCategories.map((e: any) => <option value={"/ndcCategory/" + e.id}>{e.name}</option>)
                  :
                  <></>
              }
            </select>
          </div>
          <div>
            <button type="submit" onClick={handleSubmitClick}>登録</button>
          </div>
        </div>
      </form>
    </>
  )
}
