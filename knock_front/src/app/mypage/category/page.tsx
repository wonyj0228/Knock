'use client';

import { useAppDispatch } from '@/redux/store';
import styles from './page.module.scss';
import { apiRequest } from '@/utils/api';
import { notFound } from 'next/navigation';
import { useEffect, useState } from 'react';
import { alarmCategoryList, categoryToText } from '@/utils/alarm';

export default function Page() {
  const dispatch = useAppDispatch();
  const [category, setCategory] = useState<string>();
  const [isFirst, setIsFirst] = useState(true);

  const handelChangeCategory = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCategory(e.target.value);
  };

  const getSubCategory = async () => {
    const response = await apiRequest(
      `${process.env.NEXT_PUBLIC_API_BACKEND_URL}/user/getSubCategory`,
      dispatch,
      {
        method: 'GET',
      }
    );
    console.log(response);

    if (!response.ok) {
      notFound();
    }

    const data = await response.text();
    setCategory(data.toLowerCase());
  };

  const setNewAlarmSetting = async () => {
    const response = await apiRequest(
      `${process.env.NEXT_PUBLIC_API_BACKEND_URL}/user/changeCategory`,
      dispatch,
      {
        method: 'POST',
        body: JSON.stringify({ value: category }),
      }
    );

    if (!response.ok) {
      return <div>에러</div>;
    }
  };

  useEffect(() => {
    getSubCategory();
  }, []);

  useEffect(() => {
    if (!category) return;
    if (isFirst) {
      setIsFirst(false);
    } else {
      setNewAlarmSetting();
    }
  }, [category]);

  return (
    <div className={styles.container}>
      <div className={styles.div__about}>
        회원님이 설정한 카테고리가 메인 화면에 노출됩니다.
        <br />
        선호 카테고리를 설정해주세요.
      </div>
      <form>
        <div className={styles.div__radio_box}>
          {alarmCategoryList.map((setting) => {
            return (
              <div key={`div__${setting}`}>
                <input
                  type="radio"
                  id={setting}
                  value={setting}
                  checked={category === setting}
                  onChange={handelChangeCategory}
                />
                <label htmlFor={setting}>{categoryToText[setting]}</label>
              </div>
            );
          })}
        </div>
      </form>
    </div>
  );
}
