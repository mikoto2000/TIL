import { createCalendarView } from './calendar.mjs';

document.addEventListener('DOMContentLoaded', function() {

  // カレンダーの div を取得
  const calendarDiv1 = document.getElementById('calendarContainer1');
  calendarDiv1.className = 'calendarContainer1';
  const calendarDiv2 = document.getElementById('calendarContainer2');
  calendarDiv2.className = 'calendarContainer2';

  // カレンダーの div が存在する場合のみ処理を続ける
  if (!calendarDiv1 || !calendarDiv2) {
    console.error('Calendar div not found');
    return;
  }

  const currentDate = new Date();
  const calendar1 = createCalendarView(currentDate.getFullYear(), currentDate.getMonth());
  calendarDiv1.appendChild(calendar1);
  const calendar2 = createCalendarView(currentDate.getFullYear(), currentDate.getMonth() + 1);
  calendarDiv1.appendChild(calendar2);
});


