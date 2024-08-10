import { invoke } from '@tauri-apps/api/core'

export async function hello(value: string): Promise<string | null> {
  return await invoke<{message?: string}>('plugin:helloworld|hello', {})
    .then((r) => (r.message ? r.message : null));
}
