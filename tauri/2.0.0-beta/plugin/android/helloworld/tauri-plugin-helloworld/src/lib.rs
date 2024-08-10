use tauri::{
  plugin::{Builder, TauriPlugin},
  Manager, Runtime,
};

pub use models::*;

#[cfg(desktop)]
mod desktop;
#[cfg(mobile)]
mod mobile;

mod commands;
mod error;
mod models;

pub use error::{Error, Result};

#[cfg(desktop)]
use desktop::Helloworld;
#[cfg(mobile)]
use mobile::Helloworld;

/// Extensions to [`tauri::App`], [`tauri::AppHandle`] and [`tauri::Window`] to access the helloworld APIs.
pub trait HelloworldExt<R: Runtime> {
  fn helloworld(&self) -> &Helloworld<R>;
}

impl<R: Runtime, T: Manager<R>> crate::HelloworldExt<R> for T {
  fn helloworld(&self) -> &Helloworld<R> {
    self.state::<Helloworld<R>>().inner()
  }
}

/// Initializes the plugin.
pub fn init<R: Runtime>() -> TauriPlugin<R> {
  Builder::new("helloworld")
    .invoke_handler(tauri::generate_handler![commands::hello])
    .setup(|app, api| {
      #[cfg(mobile)]
      let helloworld = mobile::init(app, api)?;
      #[cfg(desktop)]
      let helloworld = desktop::init(app, api)?;
      app.manage(helloworld);
      Ok(())
    })
    .build()
}
