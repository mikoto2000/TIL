import { initializeApp } from "firebase/app";
import { addDoc, collection, getDocs, getFirestore } from "firebase/firestore";

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

async function fetchDocument() {
  const docs = await getDocs(testCollection);
  const documentsElm = document.getElementById('documents');
  if (documentsElm) {
    docs.forEach((doc) => {

      // テンプレ取得
      const template = document.getElementById('doc-template') as HTMLTemplateElement | null;
      if (!template) {
        console.error('#doc-template not found.');
        return
      }

      // 追加するために複製
      const appendNode = template.content.cloneNode(true) as DocumentFragment;

      // 複製したノードに値を注入
      const data: any = doc.data();
      const documentIdElm = appendNode.querySelector('.document-id');
      if (documentIdElm) {
        documentIdElm.textContent = doc.id;
      }
      const documentTitleElm = appendNode.querySelector('.document-title');
      if (documentTitleElm) {
        documentTitleElm.textContent = data.title;
      }
      const documentDetailElm = appendNode.querySelector('.document-detail');
      if (documentDetailElm) {
        documentDetailElm.textContent = data.detail;
      }

      documentsElm.append(appendNode);
    });
  }
}

// ドキュメント作成ボタン定義
const createButton = document.getElementById('document-create-button');
if (createButton) {
  createButton.addEventListener('click', addDocument);
} else {
  console.error('`#document-create-button` not found.');
}

fetchDocument();
