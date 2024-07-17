import { invoke } from '@tauri-apps/api/tauri'

export async function hello() {
  await invoke('plugin:helloworld|hello')
}
