import { Form } from "react-router";
import type { Route } from "./+types/home";
import { Welcome } from "../welcome/welcome";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}

export async function loader(params) {
  console.log("start loader");
  console.log(params);
  return "Hello, World!";
}

export async function action(request) {
  console.log("start action");
  console.log(request);
  return { ok: true };
}

export default function Home(loaderData) {
  return (
    <>
      <p>{JSON.stringify(loaderData)}</p>
      <Form method="post" navigate={false} action="./">
        <input type="text" name="title" />
        <button type="submit">Send</button>
      </Form>
      <Welcome />
    </>
  );
}
