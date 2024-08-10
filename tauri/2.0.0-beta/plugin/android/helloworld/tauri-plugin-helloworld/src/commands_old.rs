use tauri::{AppHandle, command, Runtime};

use crate::models::*;
use crate::Result;
use crate::HelloworldExt;

#[command]
pub(crate) async fn hello<R: Runtime>(
    app: AppHandle<R>,
    payload: HelloRequest,
) -> Result<HelloResponse> {
    app.helloworld().hello(payload)
}
