import { createClient } from "@supabase/supabase-js";
import { insert } from "./DbControl";

const supabase = createClient(import.meta.env.VITE_SUPABASE_URL, import.meta.env.VITE_SUPABASE_ANON_KEY);

async function init() {
  const app = document.getElementById('app');

  if (app) {
    // セッションを確認し、セッションがなければGitHubでサインイン
    const { data: session } = await supabase.auth.getSession();
    if (!session) {
      await supabase.auth.signInWithOAuth({
        provider: 'github'
      });
    }

    redraw(app);

    const insertButton = document.getElementById('insert');
    insertButton?.addEventListener('click', async () => {
      const name = document.getElementById('name') as HTMLInputElement | null;
      if (name) {
        await insert(supabase, name.value);
        redraw(app);
      }
    });

    const updateButton = document.getElementById('update');
    updateButton?.addEventListener('click', async () => {
      const id = document.getElementById('updateTargetId') as HTMLInputElement | null;
      const name = document.getElementById('updateName') as HTMLInputElement | null;
      if (id && name) {
        await supabase.from('instruments').update({ name: name.value }).eq('id', id.value);
        redraw(app);
      }
    });

    const deleteButton = document.getElementById('delete');
    deleteButton?.addEventListener('click', async () => {
      const id = document.getElementById('deleteTargetId') as HTMLInputElement | null;
      if (id) {
        await supabase.from('instruments').delete().eq('id', id.value);
        redraw(app);
      }
    });

  }

}

async function redraw(app: HTMLElement) {
  const { data } = await supabase.from("instruments").select();

  console.log(data);

  // ルート要素作成
  const root = document.createElement('div');

  // 既存の内容をクリア
  app.replaceChildren();

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

await init();
