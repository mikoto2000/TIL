import { useEffect, useState } from "react";
import { AuthorEntityControllerApiFactory, BookMasterEntityControllerApiFactory, Configuration, EntityModelAuthor, EntityModelNdcCategory, NdcCategoryEntityControllerApiFactory } from "../../api";
import { BASE_URL } from "../../config";
import { Link, useNavigate } from "react-router";

type BookMasterCreatePageProps = {
};

export const BookMasterCreatePage: React.FC<BookMasterCreatePageProps> = ({ }) => {

  const navigate = useNavigate();
  const [author, setAuthor] = useState<EntityModelAuthor[] | undefined>([]);
  const [ndcCategories, setNdcCategories] = useState<EntityModelNdcCategory[] | undefined>([]);

  useEffect(() => {
    (async () => {
      const ndcCategoryApi = NdcCategoryEntityControllerApiFactory(new Configuration(), BASE_URL);
      const ndcCategoryResult = await ndcCategoryApi.getCollectionResourceNdccategoryGet({});
      setNdcCategories(ndcCategoryResult.data._embedded?.ndcCategories);

      const authorApi = AuthorEntityControllerApiFactory(new Configuration(), BASE_URL);
      const authorResult = await authorApi.getCollectionResourceAuthorGet({});
      setAuthor(authorResult.data._embedded?.authors);
    })();
  }, []);

  const handleSubmitClick = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.preventDefault();
    console.log(event);
    const form: any = event.currentTarget.form;
    console.log(form);
    const name = form.name.value;
    const authorOptions = form.author.options
    const authors = [];
    for (var i = 0; i < authorOptions.length; i++) {
      console.log(authorOptions[i].selected);
      if (authorOptions[i].selected === true) {
        authors.push(authorOptions[i].value)
      }
    }
    console.log(authors);
    const publicationDate = form.publicationDate.value;
    const ndcCategory = form.ndcCategory.value;

    const api = BookMasterEntityControllerApiFactory(new Configuration(), BASE_URL);

    console.log(JSON.stringify({
      bookMasterRequestBody: {
        name,
        publicationDate,
        authors,
        ndcCategory
      }
    }));


    api.postCollectionResourceBookmasterPost({
      bookMasterRequestBody: {
        name,
        publicationDate,
        author: authors,
        ndcCategory
      }
    }).then((result) => {
      navigate(`/bookMasters/${(result.data as any).id}`);
    });

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
            <select name="author" multiple>
              {
                author
                  ?
                  author.map((e: any) => <option value={"/author/" + e.id}>{e.name}</option>)
                  :
                  <></>
              }
            </select>
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
      <Link to="/bookMasters">一覧に戻る</Link>
    </>
  )
}
