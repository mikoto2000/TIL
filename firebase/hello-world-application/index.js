import { initializeApp } from "firebase/app";

import { getAuth, } from "firebase/auth";
import { getFirestore, } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyBN246f4FotZ0iwlidNxFWPakVsaGFMgBM",
  authDomain: "hello-world-project-cd5b9.firebaseapp.com",
  projectId: "hello-world-project-cd5b9",
  storageBucket: "hello-world-project-cd5b9.firebasestorage.app",
  messagingSenderId: "1028786778",
  appId: "1:1028786778:web:5c52b662014062a9632663"
};

const app = initializeApp(firebaseConfig);

const auth = getAuth(app);
const db = getFirestore(app);

let currentUid: string | null = null;

async function login() {
  const provider = new GoogleAuthProvider();
  const result = await signInWithPopup(auth, provider);
  const user = result.user;
  console.log("email:", user.email);
  console.log("uid:", user.uid);
  currentUid = user.uid;
}

async function saveLink() {
  if (!currentUid) {
    alert("ログインしてください！");
    return;
  }

  await addDoc(collection(db, "readlater"), {
    url: "https://example.com",
    title: "Example Site",
    createdAt: Date.now(),
  });
  alert("保存しました！");
}

async function fetchLinks() {
  const snapshot = await getDocs(collection(db, "readlater"));
  snapshot.forEach(doc => {
    console.log(doc.id, doc.data());
  });
}

document.getElementById("loginBtn")?.addEventListener("click", login);
document.getElementById("saveBtn")?.addEventListener("click", saveLink);
document.getElementById("fetchBtn")?.addEventListener("click", fetchLinks);

