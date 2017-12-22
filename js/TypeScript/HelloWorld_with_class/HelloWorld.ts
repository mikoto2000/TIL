class Student {
    fullName: string;

    // 引数に `public` をつけることで、フィールドを自動で作ってくれる。
    // 書く量は減るけどわかりにくいのでは？
    constructor(public firstName: string, public middleInitial: string, public lastName: string) {
        this.fullName = firstName + " " + middleInitial + " " + lastName;
    }
}

interface Person {
    firstName: string;
    lastName: string;
}

function greeter(person: Person) {
    return "Hello, " + person.firstName + " " + person.lastName + "!";
}

let user = new Student("Mikoto", "M.", "Ohyuki");

console.log(greeter(user));

