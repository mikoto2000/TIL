use serde::de::DeserializeOwned;
use tauri::{
  plugin::{PluginApi, PluginHandle},
  AppHandle, Runtime,
};

use crate::models::*;

#[cfg(target_os = "android")]
const PLUGIN_IDENTIFIER: &str = "com.plugin.helloworld";

#[cfg(target_os = "ios")]
tauri::ios_plugin_binding!(init_plugin_helloworld);

// initializes the Kotlin or Swift plugin classes
pub fn init<R: Runtime, C: DeserializeOwned>(
  _app: &AppHandle<R>,
  api: PluginApi<R, C>,
) -> crate::Result<Helloworld<R>> {
  #[cfg(target_os = "android")]
  let handle = api.register_android_plugin(PLUGIN_IDENTIFIER, "HelloWorldPlugin")?;
  #[cfg(target_os = "ios")]
  let handle = api.register_ios_plugin(init_plugin_helloworld)?;
  Ok(Helloworld(handle))
}

/// Access to the helloworld APIs.
pub struct Helloworld<R: Runtime>(PluginHandle<R>);

impl<R: Runtime> Helloworld<R> {
  pub fn hello(&self, payload: HelloRequest) -> crate::Result<HelloResponse> {
    self
      .0
      .run_mobile_plugin("hello", payload)
      .map_err(Into::into)
  }
}
