import { createClient } from "@supabase/supabase-js";

const supabase = createClient(import.meta.env.VITE_SUPABASE_URL, import.meta.env.VITE_SUPABASE_ANON_KEY);

const { data } = await supabase.from("instruments").select();

console.log(data);

const app = document.getElementById('app');

if (app) {
  // ルート要素作成
  const root = document.createElement('div');

  // タイトル追加
  const h1 = document.createElement('h1');
  h1.textContent = "Supabase Instruments";
  root.appendChild(h1);

  // リスト作成
  const ul = document.createElement('ul');
  data?.forEach(instrument => {
    const li = document.createElement('li');
    li.textContent = `id: ${instrument.id}, name: ${instrument.name}`;
    ul.appendChild(li);
  });
  root.appendChild(ul);

  // ルート要素をアプリケーションに追加
  app.appendChild(root);

}

