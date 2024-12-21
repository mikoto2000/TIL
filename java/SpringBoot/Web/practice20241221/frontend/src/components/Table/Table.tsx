import React, { ReactElement } from "react";

type HeaderInfo = {
  name: string,
  onClick: () => void,
}[];

type ContentInfo<T> = {
  getKeyFunc: (row: T) => string,
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
        <tr>
          {
            headerInfo.map((e) => <th
                key={e.name}
                onClick={e.onClick}
              >
                {e.name}
              </th>
            )
          }
        </tr>
      </thead>
      <tbody>
        {
          content
            ?
            React.Children.toArray(
              content.map((e) => <tr>{
                React.Children.toArray(
                  contentInfo.map((c) => <td>{c.getValueFunc(e)}</td>)
                )
              }</tr>)
            )
            :
            "表示するものがありませんでした。"
        }
      </tbody>
    </table >
  )
}
