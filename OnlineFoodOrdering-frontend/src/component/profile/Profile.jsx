import React, { useState } from 'react'
import { ProfileNavigation } from './ProfileNavigation'
import { Route, Routes } from 'react-router-dom';
import UserProfile from './UserProfile';
import Orders from './Orders';
import { Address } from './Address';
import { Favourites } from './Favourites';
import { Events } from './Events';
export const Profile = () => {
    const [openSideBar,setOpenSideBar]=useState(false);
  return (
    <div className='lg:flex justify-between'>
        <div className='sticky h-[80vh] lg:w-[20%] mt-5 '>
        <ProfileNavigation open={openSideBar}/>
        </div>
        <div className='lg:w-[80%]'>
            <Routes>
                <Route path='/' element={<UserProfile/>}/>
                <Route path='/orders' element={<Orders/>}/>
                <Route path='/address' element={<Address/>}/>
                <Route path='/favourites' element={<Favourites/>}/>
                <Route path='/events' element={<Events/>}/>
            </Routes>
        </div>

    </div>
  )
}
export default Profile;
