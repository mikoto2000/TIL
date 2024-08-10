use serde::de::DeserializeOwned;
use tauri::{plugin::PluginApi, AppHandle, Runtime};

use crate::models::*;

pub fn init<R: Runtime, C: DeserializeOwned>(
  app: &AppHandle<R>,
  _api: PluginApi<R, C>,
) -> crate::Result<Helloworld<R>> {
  Ok(Helloworld(app.clone()))
}

/// Access to the helloworld APIs.
pub struct Helloworld<R: Runtime>(AppHandle<R>);

impl<R: Runtime> Helloworld<R> {
  pub fn hello(&self) -> crate::Result<HelloResponse> {
    Ok(HelloResponse {
      value: "Hello, World!",
    })
  }
}
