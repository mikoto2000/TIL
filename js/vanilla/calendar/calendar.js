const currentDate = new Date();

document.addEventListener('DOMContentLoaded', function() {

  // カレンダーの div を取得
  const calendarDiv = document.getElementById('calendar');
  calendarDiv.className = 'calendar';

  // カレンダーの div が存在する場合のみ処理を続ける
  if (!calendarDiv) {
    console.error('Calendar div not found');
    return;
  }

  // 初期表示は今月
  const year = currentDate.getFullYear();
  const month = currentDate.getMonth();
  const firstDay = new Date(year, month, 1);
  const lastDay = new Date(year, month + 1, 0);
  const daysInMonth = lastDay.getDate();
  const firstDayOfWeek = firstDay.getDay();

  // カレンダーのヘッダーを作成
  const header = document.createElement('div');
  header.className = 'calendar-header';
  const headerText = document.createElement('h1');
  headerText.textContent = `${year}年 ${month + 1}月`;
  header.appendChild(headerText);
  calendarDiv.appendChild(header);

  // カレンダー初週の Row を作成
  let day = 1 - firstDayOfWeek;
  let firstRowDays = [];
  for (let i = 0; i < 7; i++) {
    firstRowDays.push({
      dayNumber: new Date(year, month, day++).getDate(),
      plans: []
    });
  }
  const firstRow = calendarRow(firstRowDays);
  calendarDiv.appendChild(firstRow);

  while (day <= daysInMonth) {
    let rowDays = [];
    for (let i = 0; i < 7; i++) {
      rowDays.push({
        dayNumber: new Date(year, month, day++).getDate(),
        plans: []
      });
    }
    const row = calendarRow(rowDays);
    calendarDiv.appendChild(row);
  }
});

const calendarRow = (days) => {
  const calendarRow = document.createElement('div');
  calendarRow.className = 'calendar-row';
  days.forEach(day => {
    const dayNumber = day.dayNumber;
    const plans = day.plans;
    const cell = calendarCell(day.dayNumber, plans);
    calendarRow.appendChild(cell);
  });
  return calendarRow;
}


const calendarCell = (day, plans) => {
  const cell = document.createElement('div');
  cell.className = 'calendar-cell';

  // 見出し
  const header = document.createElement('h1');
  header.textContent = day != null ? day : '';

  // プランのリスト
  const planList = document.createElement('ul');
  planList.className = 'plan-list';
  plans.forEach(plan => {
    const listItem = document.createElement('li');
    listItem.textContent = plan;
    planList.appendChild(listItem);
  });

  // 組み立て
  cell.appendChild(header);
  cell.appendChild(planList);
  return cell;
}
