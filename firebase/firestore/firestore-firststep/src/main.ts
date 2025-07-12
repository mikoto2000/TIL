import { initializeApp } from "firebase/app";
import { addDoc, collection, getFirestore } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyCtgjntK9RJ266HtARxgqynO3UFZl3SwZE",
  authDomain: "firestore-firststep.firebaseapp.com",
  projectId: "firestore-firststep",
  storageBucket: "firestore-firststep.firebasestorage.app",
  messagingSenderId: "422794914716",
  appId: "1:422794914716:web:02cc22a4b84c9ce9ff7659"
};

const app = initializeApp(firebaseConfig);

// DB 取得
const db = getFirestore(app)

// コレクション定義(この時点でコレクションが存在していなくてもよい)
const testCollection = collection(db, 'test');

async function addDocument() {
  const title = (document.getElementById('document-title') as HTMLInputElement | null)?.value;
  const detail = (document.getElementById('document-detail') as HTMLTextAreaElement | null)?.value;
  await addDoc(testCollection, {
    title, detail
  });
}

const createButton = document.getElementById('document-create-button');
if (createButton) {
  createButton.addEventListener('click', addDocument);
} else {
  console.error('`#document-create-button` not found.');
}

