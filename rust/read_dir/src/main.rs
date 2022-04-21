use std::fs;

fn main() {
    // Result, Option を愚直に使った方法
    println!("Result, Option を愚直に使った方法");
    match fs::read_dir(".") {
        Err(why) => println!("{}", why.kind()),
        Ok(read_dir) => {
            for dir_entry in read_dir {
                match dir_entry {
                    Err(why) => println!("{}", why.kind()),
                    Ok(de) => {
                        match de.path().to_str() {
                            None => println!("None value"),
                            Some(path_str) => {
                                println!("{}", path_str);
                            }
                        }
                    }
                }
            }
        }
    }
    println!("");

    // unwrap を利用した方法
    println!("unwrap を利用した方法");
    let read_dir = fs::read_dir(".").unwrap();
    for dir_entry in read_dir {
        println!("{}", dir_entry.unwrap().path().to_str().unwrap());
    }
}

