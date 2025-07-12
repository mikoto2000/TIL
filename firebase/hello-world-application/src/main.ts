import { initializeApp } from "firebase/app";
import {
  getAuth,
  signInWithPopup,
  GoogleAuthProvider,
  type User,
} from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyBN246f4FotZ0iwlidNxFWPakVsaGFMgBM",
  authDomain: "hello-world-project-cd5b9.firebaseapp.com",
  projectId: "hello-world-project-cd5b9",
  storageBucket: "hello-world-project-cd5b9.firebasestorage.app",
  messagingSenderId: "1028786778",
  appId: "1:1028786778:web:5c52b662014062a9632663"
};

const app = initializeApp(firebaseConfig);

// app の情報を #app へ出力
const appElm = document.getElementById('app');
if (appElm) {
  appElm.innerHTML = JSON.stringify(app);
}

const auth = getAuth(app);

let currentUser: User | null = null;

async function login() {
  const provider = new GoogleAuthProvider();
  const result = await signInWithPopup(auth, provider);
  currentUser = result.user;
}

// ログイン処理実行
await login();

// uid の情報を #uid へ出力
const uidElm = document.getElementById('uid');
if (uidElm) {
  uidElm.innerHTML = JSON.stringify(currentUser);
}

