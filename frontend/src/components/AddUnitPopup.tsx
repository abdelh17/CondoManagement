import React, { ChangeEvent, useState } from 'react';
import { UnitStatus, UnitType } from '../types/enums';
import { Unit } from '../types/types';
import useAxiosPrivate from '../hooks/useAxiosPrivate';

interface AddPropertyPopupProps {
    closeModal: () => void;
    userId: number;
    buildingId: number;
}

const AddUnitPopup: React.FC<AddPropertyPopupProps> = ({ closeModal, userId, buildingId }: AddPropertyPopupProps) => {
    const [unit, setUnit] = useState<Unit>({
        name: "",
        address: "",
        unit_number: 0,
        condoType: UnitType.APARTMENT,
        registration_key: null,
        status: UnitStatus.AVAILABLE_FOR_RENT,
        userId: undefined,
        building_id: undefined,
        parking_id: null,
        locker_id: null,
    });

    const axiosPrivate = useAxiosPrivate();


    const addUnit = async () => {
        console.log("Adding this unit: ", unit);
        try {
            await axiosPrivate.post(`/company/${userId}/building/${buildingId}/create-condo`, unit);
            closeModal();
        } catch (error) {
            console.log(error);
        }
    }

    const enableAddButton = () => {
        return unit.name.length > 0 && unit.address.length > 0 && unit.condoType.length > 0;
    }


    return (
        <div
            className="fixed inset-0 z-9999 overflow-y-auto bg-black bg-opacity-50"
            aria-labelledby="modal-title"
            role="dialog"
            aria-modal="true"
        >
            <div className="flex items-end justify-center min-h-screen px-4 pt-4 pb-20 text-center sm:block sm:p-0">
                <span className="hidden sm:inline-block sm:h-screen sm:align-middle" aria-hidden="true">
                    &#8203;
                </span>

                <div className="relative inline-block px-4 pt-5 pb-4 overflow-hidden text-left align-bottom transition-all transform bg-white rounded-lg shadow-xl sm:my-8 sm:w-full sm:max-w-sm sm:p-6 sm:align-middle">
                    <h3 className="text-lg font-medium leading-6 text-gray-800 capitalize" id="modal-title">
                        Add a new Unit
                    </h3>
                    <p className="mt-2 text-sm text-gray-500">
                        This will add a unit to your unit list
                    </p>

                    <form className="mt-4" action="#">
                        <div className="flex flex-col gap-5.5 ">
                            <div>
                                <label className="mb-3 block text-black dark:text-white">
                                    Unit Name
                                </label>
                                <input
                                    type="text"
                                    placeholder="Default Input"
                                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => { setUnit({ ...unit, name: e.target.value }) }}
                                />
                            </div>

                            <div>
                                <label className="mb-3 block text-black dark:text-white">
                                    Address
                                </label>
                                <input
                                    type="text"
                                    placeholder="Default Input"
                                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => { setUnit({ ...unit, address: e.target.value }) }}
                                />
                            </div>

                            <div>
                                <label className="mb-3 block text-black dark:text-white">
                                    Unit Number
                                </label>
                                <input
                                    type="text"
                                    placeholder="Number of Units"
                                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => { setUnit({ ...unit, unit_number: parseInt(e.target.value) }) }}
                                />
                            </div>

                            <div>
                                <label className="block text-black dark:text-white">
                                    Unit Status
                                </label>
                                <div className=" py-3">

                                    <select
                                        id="dropdown"
                                        value={unit.status ?? UnitStatus.AVAILABLE_FOR_RENT}
                                        onChange={(e: ChangeEvent<HTMLSelectElement>) => { setUnit({ ...unit, status: e.target.value as UnitStatus }) }}
                                        className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                    >
                                        {Object.keys(UnitStatus).map((status, index) => {
                                            return <option key={index}>{status}</option>
                                        })}
                                    </select>
                                </div>

                            </div>

                            <div>
                                <label className="block text-black dark:text-white">
                                    Unit Type
                                </label>
                                <div className=" py-3">

                                    <select
                                        id="dropdown"
                                        value={unit.condoType ?? UnitType.APARTMENT}
                                        onChange={(e: ChangeEvent<HTMLSelectElement>) => { setUnit({ ...unit, condoType: e.target.value as UnitType }) }}
                                        className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                    >
                                        {Object.keys(UnitType).map((condoType, index) => {
                                            return <option key={index}>{condoType}</option>
                                        })}
                                    </select>
                                </div>

                            </div>


                        </div>

                        <div className="mt-4 sm:flex sm:items-center sm:-mx-2">
                            <button
                                type="button"
                                onClick={closeModal}
                                className="w-full px-4 py-2 text-sm font-medium tracking-wide text-gray-700 capitalize transition-colors duration-300 transform border border-gray-200 rounded-md sm:w-1/2 sm:mx-2  hover:bg-gray-100 focus:outline-none focus:ring focus:ring-gray-300 focus:ring-opacity-40"
                            >
                                Cancel
                            </button>

                            <button
                                type="button"
                                onClick={() => addUnit()}
                                className={`w-full px-4 py-2 mt-3 text-sm font-medium tracking-wide text-white capitalize transition-colors duration-300 transform ${enableAddButton() ? 'bg-primary hover:bg-indigo-500' : 'bg-gray-300 hover:bg-red-500'} rounded-md sm:mt-0 sm:w-1/2 sm:mx-2  focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-40`}
                                disabled={!enableAddButton()}
                            >
                                Add
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default AddUnitPopup;