import { ReactElement } from "react";

type HeaderInfo = {
  name: string,
  onClick: () => void,
}[];

type ContentInfo<T> = {
  getValueFunc: (row: T) => string,
}[];

type TableProps<T> = {
  headerInfo: HeaderInfo,
  contentInfo: ContentInfo<T>,
  content?: T[]
};

export const Table = <T,>({ headerInfo, contentInfo, content }: TableProps<T>): ReactElement<any, any> => {

  return (
    <table>
      <thead>
        {
          headerInfo.map((e) => <th>{e.name}</th>)
        }
      </thead>
      <tbody>
        {
          content
            ?
            content.map((e) => <tr>{
              contentInfo.map((c) => <td>{c.getValueFunc(e)}</td>)
            }</tr>)
            :
            "表示するものがありませんでした。"
        }
      </tbody>
    </table >
  )
}
