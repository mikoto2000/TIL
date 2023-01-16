# react-oidc-context firststep

## start environment

```sh
docker compose up -d
```

## create project

```sh
npx create-react-app /project --template typescript
cp -r /project/* ./project/
sed -i -e "s/\"project\"/\"react-oidc-example\"/g" ./project/package.json ./project/package-lock.json
```

### add dependencies

```sh
npm install react-oidc-context
```

