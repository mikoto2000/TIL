import { useEffect } from 'react';

export function useActivityWatcher(onActivityFired: () => void) {

  useEffect(() => {
    const handleEvent = () => { onActivityFired() };
    window.addEventListener('click', handleEvent);
    window.addEventListener('touchstart', handleEvent);
    window.addEventListener('scroll', handleEvent);

    return () => {
      window.removeEventListener('click', handleEvent);
      window.removeEventListener('touchstart', handleEvent);
      window.removeEventListener('scroll', handleEvent);
    };
  }, []);
}
