import queryString from "query-string"

export const updateOrder = (currentPath: string, queryParams: any, currentSort: string, targetParam: string): string => {
  currentSort = currentSort ? currentSort : ","; 
  const [sortTarget, order] = currentSort.split(",");
  if (sortTarget === targetParam) {
    if (order === "asc") {
      queryParams["sort"] = targetParam + ",desc"
    } else {
      queryParams["sort"] = targetParam + ",asc"
    }
  } else {
    queryParams["sort"] = targetParam + ",desc"
  }
  const newQueryString = queryString.stringify(queryParams)
  console.log(currentPath + "?" + newQueryString)
  return currentPath + "?" + newQueryString
}
