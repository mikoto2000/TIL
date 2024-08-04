use diesel::{deserialize::Queryable, prelude::Insertable};
use serde::{Deserialize, Serialize};

use crate::schema::user;

#[derive(Queryable, Deserialize, Serialize, Clone, Debug)]
pub struct User {
    pub id: i32,
    pub name: String,
}

#[derive(Insertable, Queryable, Deserialize, Serialize, Clone, Debug)]
#[diesel(table_name = user)]
pub struct CreateUserParam {
    pub name: String,
}

