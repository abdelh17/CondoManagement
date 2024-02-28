import React, { useEffect } from 'react';
import Breadcrumb from '../../components/Breadcrumbs/Breadcrumb';
import DefaultLayout from '../DefaultLayout';
import { Link } from 'react-router-dom';
import { IoSettingsOutline } from "react-icons/io5";
import { IoIosAddCircleOutline } from "react-icons/io";
import { FaRegBuilding } from "react-icons/fa";
import { Image } from '@chakra-ui/react'
import condo1 from '../../images/condos/condo1.jpg';
import condo2 from '../../images/condos/condo2.jpg';
import condo3 from '../../images/condos/condo3.jpg';
import condo4 from '../../images/condos/condo4.jpg';
import condo5 from '../../images/condos/condo5.jpg';
import condo6 from '../../images/condos/condo6.jpg';
import AddPropertyPopup from '../../components/AddPropertyPopup';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import useAuth from '../../hooks/useAuth';
import { Building } from '../../types/types';
import EditPropertyPopup from '../../components/EditPropertyPopup';


const Buildings: React.FC = () => {
    const [cardCollection, setCardCollection] = React.useState<any[]>([]);
    const [buildings, setBuildings] = React.useState<Building[]>([]);
    const [addBuildingPopup, setAddBuildingPopup] = React.useState(false);
    const [editBuildingPopup, setEditBuildingPopup] = React.useState(false);
    const [selectedBuilding, setSelectedBuilding] = React.useState<Building | undefined>(undefined);
    const { auth } = useAuth();
    const axiosPrivate = useAxiosPrivate();

    useEffect(() => {
        fetchBuildings();
    }, [addBuildingPopup]);

    useEffect(() => {
        fetchBuildings();
    }, []);

    const fetchBuildings = async () => {
        try {
            const response = await axiosPrivate.get(`/company/${auth.id}/building/all`);
            console.log("Units fetched: ", response.data);
            setBuildings(response.data);
        } catch (error) {
            console.error(error);
        }
    };
    
    const toggleAddBuildingPopup = () => {
        setAddBuildingPopup(((prev) => !prev));
    };

    const handleSettingsClick = (building: Building) => {
        console.log('Settings Clicked');
        setSelectedBuilding(building);
        setEditBuildingPopup(true);
    }

    const getRandomImage = () => {
        const images = [condo1, condo2, condo3, condo4, condo5, condo6];
        const randomIndex = Math.floor(Math.random() * images.length);
        return images[randomIndex];
    };



    React.useEffect(() => {
        const cards = [
            {
                buildingId: 1,
                title: 'Building 1',
                unitCount: 20,
                link: '/manage-building/building/1',
                icon: <FaRegBuilding className="mt-1.5 text-xl" />,
                image: condo1
            },
            {
                buildingId: 2,
                title: 'Building 2',
                unitCount: 12,
                link: '/manage-building/building/2',
                icon: <FaRegBuilding className="mt-1.5 text-xl" />,
                image: condo2
            },
            {
                buildingId: 3,
                title: 'Building 3',
                unitCount: 15,
                link: '/manage-building/building/3',
                icon: <FaRegBuilding className="mt-1.5 text-xl" />,
                image: condo3
            },
            {
                buildingId: 4,
                title: 'Building 4',
                unitCount: 19,
                link: '/manage-building/building/4',
                icon: <FaRegBuilding className="mt-1.5 text-xl" />,
                image: condo4
            },
            {
                buildingId: 5,
                title: 'Building 5',
                unitCount: 18,
                link: '/manage-building/building/5',
                icon: <FaRegBuilding className="mt-1.5 text-xl" />,
                image: condo5
            },
            {
                buildingId: 6,
                title: 'Building 6',
                unitCount: 17,
                link: '/manage-building/building/6',
                icon: <FaRegBuilding className="mt-1.5 text-xl" />,
                image: condo6
            },

        ];

        setCardCollection(cards);
    }, []);

    return (
        <DefaultLayout>
            <Breadcrumb pageName="Manage Buildings" />
            <div className="flex items-center ">
                <button
                    onClick={toggleAddBuildingPopup}
                    className="flex items-center bg-primary text-white rounded-md px-3 py-2 gap-2 mb-4 hover:bg-indigo-500">
                    <span className="mr-2">Add Building</span>
                    <IoIosAddCircleOutline className="text-white text-xl" />
                </button>
            </div>

            {
                addBuildingPopup && <AddPropertyPopup closeModal={toggleAddBuildingPopup} userId={auth.id ?? ''} />
            }

            {
                editBuildingPopup && selectedBuilding && <EditPropertyPopup closeModal={() => setEditBuildingPopup(false)} userId={auth.id ?? ''} selectedBuilding={selectedBuilding} />
            }

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-6">
                {
                    buildings.map((card, index) => {
                        return (
                            <div key={index} className="bg-white rounded-md border border-gray-200 p-6 shadow-default shadow-black/5 ">
                                <Image
                                    src={getRandomImage()}
                                    alt='condo image'
                                    borderRadius='lg'
                                    objectFit='cover'
                                    width='100%'
                                    height='200px' // Adjust the height as per your requirement
                                    className='mb-4 rounded-md shadow-md shadow-black/5'
                                />
                                <div className="flex justify-between mb-10">
                                    <div>
                                        <div className="flex items-center mb-1">
                                            <div className="text-2xl font-semibold">{card.name}</div>
                                        </div>
                                        <div className="text-sm font-medium text-gray-400">{card.description} </div>
                                    </div>
                                    <div className="dropdown">
                                        <button type="button" className="dropdown-toggle text-gray-400 hover:text-gray-600"><i className="ri-more-fill"></i></button>
                                        <ul className="dropdown-menu shadow-md shadow-black/5 z-30 hidden py-1.5 rounded-md bg-white border border-gray-100 w-full max-w-[140px]">
                                            <li>
                                                <Link to="#" className="flex items-center text-[13px] py-1.5 px-4 text-gray-600 hover:text-blue-500 hover:bg-gray-50">Profile</Link>
                                            </li>
                                            <li>
                                                <Link to="#" className="flex items-center text-[13px] py-1.5 px-4 text-gray-600 hover:text-blue-500 hover:bg-gray-50">Settings</Link>
                                            </li>
                                            <li>
                                                <Link to="#" className="flex items-center text-[13px] py-1.5 px-4 text-gray-600 hover:text-blue-500 hover:bg-gray-50">Logout</Link>
                                            </li>
                                        </ul>
                                    </div>
                                    <IoSettingsOutline className="mt-1.5 text-xl text-primary cursor-pointer hover:text-red-800" onClick={() => handleSettingsClick(card)} />
                                </div>
                                <Link to={`/manage-building/building/${card.id}`} className="text-primary font-medium text-sm hover:text-red-800">Manage Units</Link>
                            </div>
                        );
                    })
                }
            </div>

        </DefaultLayout>
    );
};

export default Buildings;
