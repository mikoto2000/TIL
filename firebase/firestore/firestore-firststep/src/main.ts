import { initializeApp } from "firebase/app";
import { addDoc, collection, doc, getDocs, getFirestore, QuerySnapshot, updateDoc, type DocumentData } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyCtgjntK9RJ266HtARxgqynO3UFZl3SwZE",
  authDomain: "firestore-firststep.firebaseapp.com",
  projectId: "firestore-firststep",
  storageBucket: "firestore-firststep.firebasestorage.app",
  messagingSenderId: "422794914716",
  appId: "1:422794914716:web:02cc22a4b84c9ce9ff7659"
};

const DB_NAME = 'test';

const app = initializeApp(firebaseConfig);

// appのグローバル変数を定義
let docs: QuerySnapshot<DocumentData, DocumentData> | null = null;

// DB 取得
const db = getFirestore(app)

// コレクション定義(この時点でコレクションが存在していなくてもよい)
const testCollection = collection(db, DB_NAME);

async function addDocument() {
  const title = (document.getElementById('document-title') as HTMLInputElement | null)?.value;
  const detail = (document.getElementById('document-detail') as HTMLTextAreaElement | null)?.value;
  await addDoc(testCollection, {
    title, detail
  });
}

async function fetchDocument() {
  docs = await getDocs(testCollection);
  const documentsElm = document.getElementById('documents');
  if (documentsElm) {
    documentsElm.replaceChildren();
    docs.forEach((doc) => {

      // テンプレ取得
      const template = document.getElementById('doc-show-template') as HTMLTemplateElement | null;
      if (!template) {
        console.error('#doc-show-template not found.');
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

      // 複製したノードにイベントを登録
      const formElm = appendNode.querySelector('form');
      formElm?.setAttribute('id', doc.id);
      formElm?.addEventListener('submit', startEditDocument);

      documentsElm.append(appendNode);
    });
  }
}

async function startEditDocument(event: SubmitEvent) {
  event.preventDefault();

  // テンプレ取得
  const template = document.getElementById('doc-edit-template') as HTMLTemplateElement | null;
  if (!template) {
    console.error('#doc-edit-template not found.');
    return
  }

  // 追加するために複製
  const appendNode = template.content.cloneNode(true) as DocumentFragment;

  // 複製したノードに値を注入
  const doc = docs?.docs.find((d) => d.id === (event.currentTarget as HTMLFormElement).id);
  if (!doc) {
    console.error('Document not found for id:', (event.currentTarget as HTMLFormElement).id);
    return;
  }
  const idElm = appendNode.querySelector('.document-id');
  if (idElm) {
    idElm.textContent = doc.id;
  }
  const titleElm = appendNode.querySelector('.document-title') as HTMLInputElement | null;
  if (titleElm) {
    titleElm.value = doc.data().title || '';
  }
  const detailElm = appendNode.querySelector('.document-detail') as HTMLTextAreaElement | null;
  if (detailElm) {
    detailElm.value = doc.data().detail || '';
  }

  // 複製したノードにイベントを登録
  const formElm = appendNode.querySelector('form');
  formElm?.setAttribute('id', doc.id);
  formElm?.addEventListener('submit', updateButtonHandler);

  const cancelButton = appendNode.querySelector('.document-cancel-button');
  cancelButton?.addEventListener('click', (e) => switchToShowDocument(e.currentTarget as HTMLFormElement));

  // 追加場所を特定
  const form = event.currentTarget as HTMLFormElement;
  if (form) {
    const parentElement = form.parentElement;
    form.after(appendNode);
    parentElement?.removeChild(form);
  }
}

async function updateButtonHandler(event: SubmitEvent) {
  event.preventDefault();
  const docRef = doc(db, DB_NAME, (event.currentTarget as HTMLFormElement).id);
  await updateDoc(docRef, {
    title: ((event.currentTarget as HTMLElement).querySelector('.document-title') as HTMLInputElement).value,
    detail: ((event.currentTarget as HTMLElement).querySelector('.document-detail') as HTMLTextAreaElement).value
  });
  switchToShowDocument(event.currentTarget as HTMLFormElement);
  fetchDocument();
}

async function switchToShowDocument(form: HTMLFormElement) {

  // テンプレ取得
  const template = document.getElementById('doc-show-template') as HTMLTemplateElement | null;
  if (!template) {
    console.error('#doc-show-template not found.');
    return
  }

  // 追加するために複製
  const appendNode = template.content.cloneNode(true) as DocumentFragment;

  // 複製したノードに値を注入
  const doc = docs?.docs.find((d) => d.id === form.id);
  if (!doc) {
    console.error('Document not found for id:', form.id);
    return;
  }
  const idElm = appendNode.querySelector('.document-id');
  if (idElm) {
    idElm.textContent = doc.id;
  }
  const titleElm = appendNode.querySelector('.document-title') as HTMLInputElement | null;
  if (titleElm) {
    titleElm.textContent = doc.data().title || '';
  }
  const detailElm = appendNode.querySelector('.document-detail') as HTMLTextAreaElement | null;
  if (detailElm) {
    detailElm.textContent = doc.data().detail || '';
  }

  // 複製したノードにイベントを登録
  const formElm = appendNode.querySelector('form');
  formElm?.setAttribute('id', doc.id);
  formElm?.addEventListener('submit', startEditDocument);

  // 追加場所を特定
  if (form) {
    const parentElement = form.parentElement;
    form.after(appendNode);
    parentElement?.removeChild(form);
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
