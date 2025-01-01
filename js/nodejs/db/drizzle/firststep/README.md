# Drizzle firststep

## Install packages

```sh
npm i drizzle-orm pg dotenv
npm i -D drizzle-kit tsx @types/pg typescript
```

## Create DB setting(env file)

```env
DATABASE_URL=postgres://postgres@postgres/postgres
```

## Create TypeScript setting(tsconfig.json)

```sh
npx tsc --init
```

### set tsconfig parameters

- `outDir`: `./dist`

## Test

```sh
mkdir src
echo 'console.log("Hello, World!");' > ./src/index.ts
tsc
node ./dist/index.js
```

Printed `Hello, World!` ... ok!


## Setting drizzle

### Create connection config

```sh
mkdir -p ./src/db
cat << EOF > ./src/db/index.ts
import 'dotenv/config';
import { drizzle } from 'drizzle-orm/node-postgres';

const db = drizzle(process.env.DATABASE_URL!);
EOF

cat << EOF > ./drizzle.config.ts
import 'dotenv/config';
import { defineConfig } from 'drizzle-kit';

export default defineConfig({
  out: './drizzle',
  schema: './src/db/schema.ts',
  dialect: 'postgresql',
  dbCredentials: {
    url: process.env.DATABASE_URL!,
  },
});
EOF
```

## Create DB schema

```sh
cat << EOF > ./src/db/schema.ts
import { integer, pgTable, varchar } from "drizzle-orm/pg-core";

export const usersTable = pgTable("users", {
  id: integer().primaryKey().generatedAlwaysAsIdentity(),
  name: varchar({ length: 255 }).notNull(),
  age: integer().notNull(),
  email: varchar({ length: 255 }).notNull().unique(),
});
EOF
```


## Create and apply migration

### Create migrate file

```sh
npx drizzle-kit generate
```

Write migration file to `./drizzle`.

### Apply migrate file

```sh
npx drizzle-kit migrate
```

Create table from migrate file.


## Create main file.

```sh
cat <<"EOF" > ./src/index.ts
import 'dotenv/config';
import { drizzle } from 'drizzle-orm/node-postgres';
import { eq } from 'drizzle-orm';
import { usersTable } from './db/schema';
  
const db = drizzle(process.env.DATABASE_URL!);

async function main() {
  const user: typeof usersTable.$inferInsert = {
    name: 'John',
    age: 30,
    email: 'john@example.com',
  };

  await db.insert(usersTable).values(user);
  console.log('New user created!')

  const users = await db.select().from(usersTable);
  console.log('Getting all users from the database: ', users)
  /*
  const users: {
    id: number;
    name: string;
    age: number;
    email: string;
  }[]
  */

  await db
    .update(usersTable)
    .set({
      age: 31,
    })
    .where(eq(usersTable.email, user.email));
  console.log('User info updated!')

  await db.delete(usersTable).where(eq(usersTable.email, user.email));
  console.log('User deleted!')
}

main();
EOF
```

## Run and test

```sh
npx tsc
node ./dist/src/index.js
```

```sh
node ➜ /workspaces $ node ./dist/src/index.js
New user created!
Getting all users from the database:  [ { id: 1, name: 'John', age: 30, email: 'john@example.com' } ]
User info updated!
User deleted!
```

